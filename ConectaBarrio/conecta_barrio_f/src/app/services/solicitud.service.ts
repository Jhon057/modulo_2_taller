import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EstadoSolicitud, Solicitud, SolicitudRequest } from '../models/solicitud.model';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {
  private readonly http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8081/solicitud';

  listar(): Observable<Solicitud[]> {
    return this.http.get<Solicitud[]>(this.API_URL);
  }

  buscarPorId(id: number): Observable<Solicitud> {
    return this.http.get<Solicitud>(`${this.API_URL}/${id}`);
  }

  crear(dto: SolicitudRequest): Observable<Solicitud> {
    return this.http.post<Solicitud>(this.API_URL, dto);
  }

  actualizar(id: number, dto: SolicitudRequest): Observable<Solicitud> {
    return this.http.put<Solicitud>(`${this.API_URL}/${id}`, dto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  cambiarEstado(id: number, estado: EstadoSolicitud): Observable<string> {
    const params = new HttpParams().set('estado', estado);
    return this.http.patch(`${this.API_URL}/cambiarEstado/${id}`, null, {
      params,
      responseType: 'text'
    });
  }

  cambiarPrioridad(id: number): Observable<string> {
    return this.http.patch(`${this.API_URL}/cambiarPrioridad/${id}`, null, {
      responseType: 'text'
    });
  }
}
