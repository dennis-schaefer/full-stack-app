import axios, {type AxiosRequestConfig } from 'axios';

export const AXIOS_INSTANCE = axios.create({
    baseURL: ''
});

// Diese Funktion wird von Orval als "Mutator" genutzt
export const customInstance = <T>(config: AxiosRequestConfig, options?: AxiosRequestConfig): Promise<T> => {
    const source = axios.CancelToken.source();
    const promise = AXIOS_INSTANCE({
        ...config,
        ...options,
        cancelToken: source.token,
    }).then(({ data }) => data);

    // Ermöglicht Request-Abbruch durch React Query
    // @ts-ignore
    promise.cancel = () => {
        source.cancel('Query was cancelled');
    };

    return promise;
};