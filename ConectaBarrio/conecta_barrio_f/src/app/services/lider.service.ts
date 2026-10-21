import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Lider, LiderRequest } from '../models/lider.model';

@Injectable({
  providedIn: 'root'
})
export class LiderService {
  private readonly http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8081/lider';

  listar(): Observable<Lider[]> {
    return this.http.get<Lider[]>(this.API_URL);
  }

  buscarPorId(id: number): Observable<Lider> {
    return this.http.get<Lider>(`${this.API_URL}/${id}`);
  }

  crear(dto: LiderRequest): Observable<Lider> {
    return this.http.post<Lider>(this.API_URL, dto);
  }

  actualizar(id: number, dto: LiderRequest): Observable<Lider> {
    return this.http.put<Lider>(`${this.API_URL}/${id}`, dto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
