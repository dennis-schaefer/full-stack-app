import { useAuth } from "../auth/AuthContext.tsx";

export default function PublicView() {
    const { user, isAuthenticated, login } = useAuth();

    return (
        <div>
            <h1>Public View</h1>
            <p>Diese Seite ist öffentlich zugänglich.</p>

            {isAuthenticated ? (
                <div>
                    <p>Sie sind angemeldet als: <strong>{user?.fullName}</strong></p>
                    <a href="/">Zum Dashboard</a>
                </div>
            ) : (
                <div>
                    <p>Sie sind nicht angemeldet.</p>
                    <button onClick={login}>Anmelden</button>
                </div>
            )}
        </div>
    );
}