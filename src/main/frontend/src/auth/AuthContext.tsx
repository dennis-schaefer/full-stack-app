import { createContext, useContext } from 'react';

export interface UserInfo {
    username: string;
    email: string;
    fullName: string;
    roles: string[];
}

export interface AuthContextType {
    user: UserInfo | null;
    loading: boolean;
    isAuthenticated: boolean;
    login: () => void;
    refetchUser: () => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function useAuth(): AuthContextType {
    const context = useContext(AuthContext);
    if (!context)
        throw new Error('useAuth must be used within an AuthProvider');

    return context;
}

