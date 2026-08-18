import { useEffect, useState } from "react";
import "./App.css";
import type { InventoryItem } from "./types";

function App() {
  const [items, setItems] = useState<InventoryItem[]>([]);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [loginError, setLoginError] = useState("");

  function getAuthHeader(): string {
    return `Basic ${btoa(`${username}:${password}`)}`;
  }

  function getItems(): void {
    fetch("http://localhost:8080/api/items")
      .then((response) => response.json())
      .then((data) => setItems(data));
  }

  function handleLogin(e: React.FormEvent): void {
    e.preventDefault();
    fetch("http://localhost:8080/api/items", {
      headers: { Authorization: getAuthHeader() },
    }).then((response) => {
      if (response.ok) {
        setLoggedIn(true);
        setIsAdmin(username === "admin");
        setLoginError("");
      } else {
        setLoggedIn(false);
        setLoginError("Invalid username or password");
      }
    });
  }

  function deleteItem(id: number): void {
    fetch(`http://localhost:8080/api/items/${id}`, {
      method: "DELETE",
      headers: { Authorization: getAuthHeader() },
    }).then(() => {
      getItems();
    });
  }

  function restockItem(id: number): void {
    const amountStr = window.prompt("How much to add to stock?");
    if (amountStr === null) return;

    const amount = Number(amountStr);
    if (Number.isNaN(amount)) {
      alert("Please enter a valid number");
      return;
    }

    fetch(`http://localhost:8080/api/items/${id}/restock`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: getAuthHeader(),
      },
      body: JSON.stringify({ quantity: amount }),
    }).then(() => {
      getItems();
    });
  }

  useEffect(() => {
    getItems();
  }, []);

  return (
    <div>
      <h1>Smart Pantry Dashboard</h1>

      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Log in</button>
      </form>
      {loginError && <p style={{ color: "red" }}>{loginError}</p>}
      {loggedIn && <p>Logged in as {isAdmin ? "admin" : "user"}</p>}

      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Min Threshold</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {items.map((item) => (
            <tr key={item.id} className={item.isLowStock ? "low-stock" : ""}>
              <td>{item.name}</td>
              <td>{item.quantity}</td>
              <td>{item.minThreshold}</td>
              <td className="status-cell">
                <div className="status-inner">
                  {item.isLowStock && (
                    <span className="low-stock-badge">Low Stock</span>
                  )}
                  {isAdmin && (
                    <>
                      <button
                        className="restock-button"
                        onClick={() => restockItem(item.id)}
                      >
                        Restock
                      </button>
                      <button onClick={() => deleteItem(item.id)}>
                        Delete
                      </button>
                    </>
                  )}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
