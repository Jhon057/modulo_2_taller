import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { SolicitudesComponent } from '../solicitudes/solicitudes';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, SolicitudesComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {
  readonly authService = inject(AuthService);

  logout(): void {
    this.authService.logout();
  }
}
