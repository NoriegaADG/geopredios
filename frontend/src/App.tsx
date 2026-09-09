import { useEffect, useState } from 'react'

type HealthResponse = {
  status: string
  application: string
  database: { status: string }
}

function App() {
  const [health, setHealth] = useState<HealthResponse | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_BASE_URL}/api/v1/health`)
      .then((res) => res.json())
      .then(setHealth)
      .catch((err) => setError(err.message))
  }, [])

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
      <h1>GeoPredios</h1>
      {error && <p style={{ color: 'red' }}>Error: {error}</p>}
      {health ? (
        <pre>{JSON.stringify(health, null, 2)}</pre>
      ) : (
        !error && <p>Cargando estado del backend...</p>
      )}
    </div>
  )
}

export default App