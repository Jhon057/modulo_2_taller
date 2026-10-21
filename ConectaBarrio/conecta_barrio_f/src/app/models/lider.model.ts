export interface Lider {
  id: number;
  nombre: string;
  email: string;
  telefono?: string;
}

export interface LiderRequest {
  nombre: string;
  email: string;
  telefono?: string;
}
