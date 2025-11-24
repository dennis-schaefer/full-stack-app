export default interface Todo {
    id: number;
    title: string;
    createdBy: string;
    createdAt: string;
    completedBy: string | undefined;
    completedAt: string | undefined;
}