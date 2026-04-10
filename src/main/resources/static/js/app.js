const API = "http://localhost:8080";

async function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const res = await fetch(`${API}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  });

  const data = await res.json();
  if (res.ok) {
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.userId);
    document.getElementById("msg").innerText = "Login successful";
    window.location.href = "/dashboard.html";
  } else {
    document.getElementById("msg").innerText = data.error || "Login failed";
  }
}
