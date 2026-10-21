import { Lider } from './lider.model';

export type EstadoSolicitud = 'PENDIENTE' | 'EN_PROCESO' | 'RESUELTA' | 'CANCELADA';

export interface Solicitud {
  id: number;
  titulo: string;
  descripcion: string;
  prioridad: boolean;
  estado: EstadoSolicitud;
  fechaCreacion: string;
  lider?: Lider;
}

export interface SolicitudRequest {
  titulo: string;
  descripcion: string;
  idLider: number;
}
