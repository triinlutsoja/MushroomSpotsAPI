const map = L.map('map').setView([58.5953, 25.0136], 7); // Centered on Estonia

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '&copy; OpenStreetMap contributors',
}).addTo(map);