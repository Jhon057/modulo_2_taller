import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import {
  AuthResponse,
  JwtPayload,
  LoginRequest,
  RegistroRequest,
  Usuario,
} from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  private readonly API_URL = 'http://localhost:8081/api/auth';
  private readonly TOKEN_KEY = 'cb_auth_token';

  // Signals
  readonly token = signal<string | null>(this.getStoredToken());
  readonly currentUser = signal<Usuario | null>(this.getUserFromToken(this.token()));
  readonly isAuthenticated = computed(() => !!this.token() && !!this.currentUser());

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API_URL}/login`, credentials)
      .pipe(tap((res) => this.handleAuthentication(res.token)));
  }

  registro(request: RegistroRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API_URL}/registro`, request)
      .pipe(tap((res) => this.handleAuthentication(res.token)));
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.token.set(null);
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  private handleAuthentication(token: string): void {
    if (token) {
      localStorage.setItem(this.TOKEN_KEY, token);
      this.token.set(token);
      this.currentUser.set(this.getUserFromToken(token));
    }
  }

  private getStoredToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private getUserFromToken(token: string | null): Usuario | null {
    if (!token) return null;
    try {
      const parts = token.split('.');
      if (parts.length !== 3) return null;

      // Base64Url decode
      const base64Url = parts[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join(''),
      );

      const decoded: JwtPayload = JSON.parse(jsonPayload);
      if (!decoded || !decoded.sub) return null;

      // Check expiration if present
      if (decoded.exp && decoded.exp * 1000 < Date.now()) {
        localStorage.removeItem(this.TOKEN_KEY);
        return null;
      }

      return {
        username: decoded.sub,
        rol: decoded.rol,
      };
    } catch {
      return null;
    }
  }
}
