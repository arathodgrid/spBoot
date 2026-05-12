import { useEffect, useState } from "react";

function App() {
  const [users, setUsers] = useState([]);
  const [name, setName] = useState("");

  const [editingId, setEditingId] = useState(null);
  const [editingName, setEditingName] = useState("");

  // READ
  const loadUsers = async () => {
    const res = await fetch("http://localhost:8080/users");
    const data = await res.json();
    setUsers(data);
  };

  // CREATE
  const addUser = async () => {
    if (!name.trim()) {
      alert("Name cannot be empty");
      return;
    }

    await fetch("http://localhost:8080/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name }),
    });

    setName("");
    loadUsers();
  };

  // DELETE
  const deleteUser = async (id) => {
    await fetch(`http://localhost:8080/users/${id}`, {
      method: "DELETE",
    });

    loadUsers();
  };

  // START EDIT
  const startEdit = (user) => {
    setEditingId(user.id);
    setEditingName(user.name);
  };

  // UPDATE
  const updateUser = async () => {
    if (!editingName.trim()) {
      alert("Name cannot be empty");
      return;
    }

    await fetch(`http://localhost:8080/users/${editingId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name: editingName }),
    });

    setEditingId(null);
    setEditingName("");
    loadUsers();
  };

  // LOAD ON START
  useEffect(() => {
    loadUsers();
  }, []);

  return (
      <div style={{ padding: "30px", fontFamily: "Arial" }}>
        <h1>User Management</h1>

        {/* CREATE */}
        <input
            type="text"
            placeholder="Enter name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            style={{ padding: "6px", marginRight: "10px" }}
        />

        <button onClick={addUser}>Add User</button>

        <br /><br />

        {/* TABLE */}
        <table
            border="1"
            cellPadding="10"
            style={{ borderCollapse: "collapse", width: "50%" }}
        >
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
          </tr>
          </thead>

          <tbody>
          {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>

                <td>
                  {editingId === user.id ? (
                      <input
                          value={editingName}
                          onChange={(e) => setEditingName(e.target.value)}
                      />
                  ) : (
                      user.name
                  )}
                </td>

                <td>
                  {editingId === user.id ? (
                      <button onClick={updateUser}>Save</button>
                  ) : (
                      <button onClick={() => startEdit(user)}>Edit</button>
                  )}

                  <button
                      onClick={() => deleteUser(user.id)}
                      style={{ marginLeft: "10px" }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
}

export default App;