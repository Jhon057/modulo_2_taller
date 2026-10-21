import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Rol } from '../../models/auth.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  isLoginMode = signal<boolean>(true);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  // Form Models
  username = '';
  password = '';
  rol: Rol = 'USER';

  readonly roles: { label: string; value: Rol }[] = [
    { label: 'Usuario Vecino', value: 'USER' },
    { label: 'Líder Comunitario', value: 'LIDER' },
    { label: 'Administrador', value: 'ADMIN' },
  ];

  toggleMode(): void {
    this.isLoginMode.update((mode) => !mode);
    this.errorMessage.set(null);
    this.successMessage.set(null);
  }

  onSubmit(): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);

    if (!this.username.trim() || !this.password.trim()) {
      this.errorMessage.set('Por favor, completa todos los campos.');
      return;
    }

    this.isLoading.set(true);

    if (this.isLoginMode()) {
      this.authService.login({ username: this.username, password: this.password }).subscribe({
        next: () => {
          this.isLoading.set(false);
          void this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.isLoading.set(false);
          if (err.status === 401) {
            this.errorMessage.set('Usuario o contraseña incorrectos.');
          } else if (err.error?.error) {
            this.errorMessage.set(err.error.error);
          } else {
            this.errorMessage.set('No se pudo conectar con el servidor. Intenta nuevamente.');
          }
        },
      });
    } else {
      this.authService
        .registro({ username: this.username, password: this.password, rol: this.rol })
        .subscribe({
          next: () => {
            this.isLoading.set(false);
            this.successMessage.set('¡Registro exitoso! Redirigiendo...');
            setTimeout(() => {
              void this.router.navigate(['/dashboard']);
            }, 1000);
          },
          error: (err) => {
            this.isLoading.set(false);
            if (err.error?.error) {
              this.errorMessage.set(err.error.error);
            } else if (err.error?.message) {
              this.errorMessage.set(err.error.message);
            } else {
              this.errorMessage.set('Error al registrar usuario. Comprueba los datos.');
            }
          },
        });
    }
  }
}
