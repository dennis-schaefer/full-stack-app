import {type ReactNode, useEffect, useState } from 'react';
import { AuthContext, type UserInfo } from './AuthContext';

interface AuthProviderProps {
    children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
    const [user, setUser] = useState<UserInfo | null>(null);
    const [loading, setLoading] = useState(true);

    const fetchUserInfo = async () => {
        try {
            const response = await fetch('/api/v1/userinfo', {
                credentials: 'include'
            });

            if (!response.ok) {
                setUser(null);
                return;
            }

            const data = await response.json();

            setUser(null);
            if (data && data.username)
                setUser(data);

        } catch (error) {
            console.error('Error fetching user info:', error);
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUserInfo().then();
    }, []);

    const login = () => {
        window.location.href = '/oauth2/authorization/keycloak';
    };

    const refetchUser = async () => {
        setLoading(true);
        await fetchUserInfo();
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                loading,
                isAuthenticated: user !== null,
                login,
                refetchUser
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

