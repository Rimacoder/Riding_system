const API_BASE = window.location.origin;

function setMsg(id, text, type = "") {
  const el = document.getElementById(id);
  if (!el) return;
  el.className = `msg ${type}`.trim();
  el.innerText = text;
}

function getToken() {
  return localStorage.getItem("token") || "";
}

function authHeaders() {
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${getToken()}`
  };
}

function logout() {
  localStorage.clear();
  window.location.href = "/login.html";
}

async function registerUser() {
  const payload = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    phone: document.getElementById("phone").value,
    role: document.getElementById("role").value,
    vehicleModel: document.getElementById("vehicleModel").value || null,
    licensePlate: document.getElementById("licensePlate").value || null,
    vehicleCapacity: document.getElementById("vehicleCapacity").value ? Number(document.getElementById("vehicleCapacity").value) : null
  };

  try {
    const res = await fetch(`${API_BASE}/api/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    const data = await res.json();

    if (!res.ok) {
      setMsg("registerMsg", data.error || "Registration failed", "error");
      return;
    }

    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", String(data.userId));
    localStorage.setItem("role", data.role);
    localStorage.setItem("name", data.name);
    setMsg("registerMsg", "Registration successful. Redirecting...", "success");
    setTimeout(() => window.location.href = "/dashboard.html", 700);
  } catch {
    setMsg("registerMsg", "Server error", "error");
  }
}

async function loginUser() {
  const payload = {
    email: document.getElementById("email").value,
    password: document.getElementById("password").value
  };

  try {
    const res = await fetch(`${API_BASE}/api/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    const data = await res.json();

    if (!res.ok) {
      setMsg("loginMsg", data.error || "Login failed", "error");
      return;
    }

    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", String(data.userId));
    localStorage.setItem("role", data.role);
    localStorage.setItem("name", data.name);
    setMsg("loginMsg", "Login successful. Redirecting...", "success");
    setTimeout(() => window.location.href = "/dashboard.html", 700);
  } catch {
    setMsg("loginMsg", "Server error", "error");
  }
}

function ensureAuth() {
  if (!getToken()) {
    window.location.href = "/login.html";
  }
}

function loadDashboard() {
  ensureAuth();
  const name = localStorage.getItem("name") || "User";
  const role = localStorage.getItem("role") || "N/A";
  const userId = localStorage.getItem("userId") || "N/A";
  document.getElementById("welcome").innerText = `Welcome, ${name}`;
  document.getElementById("meta").innerHTML = `<span class="pill">Role: ${role}</span> <span class="pill">User ID: ${userId}</span>`;
}

async function postRide() {
  const driverId = localStorage.getItem("userId");
  const payload = {
    source: document.getElementById("source").value,
    destination: document.getElementById("destination").value,
    travelDate: document.getElementById("travelDate").value,
    travelTime: document.getElementById("travelTime").value,
    availableSeats: Number(document.getElementById("availableSeats").value),
    baseFare: Number(document.getElementById("baseFare").value),
    ratePerKm: Number(document.getElementById("ratePerKm").value)
  };

  try {
    const res = await fetch(`${API_BASE}/api/rides/driver/${driverId}`, {
      method: "POST",
      headers: authHeaders(),
      body: JSON.stringify(payload)
    });
    const data = await res.json();
    if (!res.ok) {
      setMsg("rideMsg", data.error || "Ride posting failed", "error");
      return;
    }
    setMsg("rideMsg", `Ride posted successfully with ID ${data.id}`, "success");
  } catch {
    setMsg("rideMsg", "Server error", "error");
  }
}

async function searchRides() {
  const source = document.getElementById("searchSource").value;
  const destination = document.getElementById("searchDestination").value;
  const date = document.getElementById("searchDate").value;

  const res = await fetch(`${API_BASE}/api/rides/search?source=${encodeURIComponent(source)}&destination=${encodeURIComponent(destination)}&date=${date}`, {
    headers: authHeaders()
  });
  const rides = await res.json();

  const body = document.getElementById("ridesBody");
  body.innerHTML = "";

  if (!Array.isArray(rides) || rides.length === 0) {
    body.innerHTML = `<tr><td colspan="7">No rides found</td></tr>`;
    return;
  }

  rides.forEach((ride) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${ride.id}</td>
      <td>${ride.source}</td>
      <td>${ride.destination}</td>
      <td>${ride.travelDate}</td>
      <td>${ride.travelTime}</td>
      <td>${ride.availableSeats}</td>
      <td><button onclick="bookRide(${ride.id})">Book</button></td>
    `;
    body.appendChild(tr);
  });
}

async function bookRide(rideId) {
  const seats = Number(prompt("Seats required?", "1"));
  const distanceKm = Number(prompt("Distance in KM?", "10"));

  if (!seats || !distanceKm) return;

  const payload = {
    rideId,
    passengerId: Number(localStorage.getItem("userId")),
    seatsBooked: seats,
    distanceKm
  };

  const res = await fetch(`${API_BASE}/api/bookings`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(payload)
  });

  const data = await res.json();
  if (!res.ok) {
    alert(data.error || "Booking failed");
    return;
  }

  alert(`Booking confirmed. Booking ID: ${data.id}, Fare: ${data.fareAmount}`);
}
