import type { ReactNode } from 'react';
import { useAuth } from './AuthContext';

interface ProtectedRouteProps {
    children: ReactNode;
}

export function ProtectedRoute({ children }: ProtectedRouteProps) {
    const { isAuthenticated, loading, login } = useAuth();

    if (loading)
        return <div>Lädt...</div>;

    if (!isAuthenticated) {
        login();
        return null;
    }

    return <>{children}</>;
}

