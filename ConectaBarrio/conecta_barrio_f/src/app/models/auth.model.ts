export type Rol = 'ADMIN' | 'USER' | 'LIDER';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegistroRequest {
  username: string;
  password: string;
  rol: Rol;
}

export interface AuthResponse {
  token: string;
}

export interface Usuario {
  username: string;
  rol: Rol;
}

export interface JwtPayload {
  sub: string;
  rol: Rol;
  exp?: number;
  iat?: number;
}
