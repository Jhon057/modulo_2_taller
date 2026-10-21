import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Lider } from '../../models/lider.model';
import { EstadoSolicitud, Solicitud, SolicitudRequest } from '../../models/solicitud.model';
import { AuthService } from '../../services/auth.service';
import { LiderService } from '../../services/lider.service';
import { SolicitudService } from '../../services/solicitud.service';

@Component({
  selector: 'app-solicitudes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './solicitudes.html',
  styleUrl: './solicitudes.css',
})
export class SolicitudesComponent implements OnInit {
  private readonly solicitudService = inject(SolicitudService);
  private readonly liderService = inject(LiderService);
  readonly authService = inject(AuthService);

  // Estado general
  solicitudes = signal<Solicitud[]>([]);
  lideres = signal<Lider[]>([]);
  isLoading = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  // Filtros
  filtroTexto = signal<string>('');
  filtroEstado = signal<string>('TODOS');
  filtroPrioridad = signal<string>('TODOS');

  // Modales
  modalAbierto = signal<boolean>(false);
  modalEliminarAbierto = signal<boolean>(false);
  modoEdicion = signal<boolean>(false);
  solicitudSeleccionada = signal<Solicitud | null>(null);

  // Campos de formulario
  formTitulo = '';
  formDescripcion = '';
  formIdLider: number | null = null;
  formError = signal<string | null>(null);

  // Permisos y rol
  currentUser = computed(() => this.authService.currentUser());
  userRole = computed(() => this.currentUser()?.rol || 'USER');
  puedeCambiarEstado = computed(() => ['ADMIN', 'LIDER'].includes(this.userRole()));

  // Listado filtrado
  solicitudesFiltradas = computed(() => {
    const texto = this.filtroTexto().toLowerCase().trim();
    const estado = this.filtroEstado();
    const prioridad = this.filtroPrioridad();

    return this.solicitudes().filter((s) => {
      const coincideTexto =
        !texto ||
        s.titulo.toLowerCase().includes(texto) ||
        s.descripcion.toLowerCase().includes(texto) ||
        s.lider?.nombre.toLowerCase().includes(texto);

      const coincideEstado = estado === 'TODOS' || s.estado === estado;

      const coincidePrioridad =
        prioridad === 'TODOS' ||
        (prioridad === 'ALTA' && s.prioridad) ||
        (prioridad === 'NORMAL' && !s.prioridad);

      return coincideTexto && coincideEstado && coincidePrioridad;
    });
  });

  // Estadísticas
  totalSolicitudes = computed(() => this.solicitudes().length);
  pendientes = computed(() => this.solicitudes().filter((s) => s.estado === 'PENDIENTE').length);
  enProceso = computed(() => this.solicitudes().filter((s) => s.estado === 'EN_PROCESO').length);
  resueltas = computed(() => this.solicitudes().filter((s) => s.estado === 'RESUELTA').length);

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    // Cargar líderes
    this.liderService.listar().subscribe({
      next: (data) => {
        this.lideres.set(data || []);
      },
      error: (err) => {
        console.error('Error al cargar líderes:', err);
      },
    });

    // Cargar solicitudes
    this.solicitudService.listar().subscribe({
      next: (data) => {
        this.solicitudes.set(data || []);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        if (err.status === 403) {
          this.errorMessage.set('No tienes permisos suficientes para consultar las solicitudes.');
        } else {
          this.errorMessage.set('No se pudieron cargar las solicitudes. Intente nuevamente.');
        }
      },
    });
  }

  abrirModalCrear(): void {
    this.modoEdicion.set(false);
    this.solicitudSeleccionada.set(null);
    this.formTitulo = '';
    this.formDescripcion = '';
    this.formIdLider = this.lideres().length > 0 ? this.lideres()[0].id : null;
    this.formError.set(null);
    this.modalAbierto.set(true);
  }

  abrirModalEditar(solicitud: Solicitud): void {
    this.modoEdicion.set(true);
    this.solicitudSeleccionada.set(solicitud);
    this.formTitulo = solicitud.titulo;
    this.formDescripcion = solicitud.descripcion;
    this.formIdLider = solicitud.lider
      ? solicitud.lider.id
      : this.lideres().length > 0
        ? this.lideres()[0].id
        : null;
    this.formError.set(null);
    this.modalAbierto.set(true);
  }

  cerrarModal(): void {
    this.modalAbierto.set(false);
    this.formError.set(null);
  }

  guardarSolicitud(): void {
    this.formError.set(null);

    if (!this.formTitulo.trim()) {
      this.formError.set('El título de la solicitud es obligatorio.');
      return;
    }

    if (!this.formDescripcion.trim()) {
      this.formError.set('La descripción de la solicitud es obligatoria.');
      return;
    }

    if (!this.formIdLider) {
      this.formError.set('Debes seleccionar y asignar un líder a la solicitud.');
      return;
    }

    const payload: SolicitudRequest = {
      titulo: this.formTitulo.trim(),
      descripcion: this.formDescripcion.trim(),
      idLider: Number(this.formIdLider),
    };

    this.isSubmitting.set(true);

    if (this.modoEdicion()) {
      const actual = this.solicitudSeleccionada();
      if (!actual) return;

      this.solicitudService.actualizar(actual.id, payload).subscribe({
        next: (actualizada) => {
          this.isSubmitting.set(false);
          this.cerrarModal();
          this.mostrarNotificacion('¡Solicitud actualizada y asignada exitosamente!');
          this.cargarDatos();
        },
        error: (err) => {
          this.isSubmitting.set(false);
          if (err.error?.error) {
            this.formError.set(err.error.error);
          } else if (err.error?.message) {
            this.formError.set(err.error.message);
          } else {
            this.formError.set('Error al actualizar la solicitud. Verifica los datos.');
          }
        },
      });
    } else {
      this.solicitudService.crear(payload).subscribe({
        next: (creada) => {
          this.isSubmitting.set(false);
          this.cerrarModal();
          this.mostrarNotificacion('¡Solicitud creada y asignada con éxito!');
          this.cargarDatos();
        },
        error: (err) => {
          this.isSubmitting.set(false);
          if (err.error?.error) {
            this.formError.set(err.error.error);
          } else if (err.error?.message) {
            this.formError.set(err.error.message);
          } else {
            this.formError.set(
              'Error al registrar la solicitud. Verifica que el título no esté duplicado.',
            );
          }
        },
      });
    }
  }

  abrirConfirmacionEliminar(solicitud: Solicitud): void {
    this.solicitudSeleccionada.set(solicitud);
    this.modalEliminarAbierto.set(true);
  }

  cerrarModalEliminar(): void {
    this.modalEliminarAbierto.set(false);
    this.solicitudSeleccionada.set(null);
  }

  confirmarEliminar(): void {
    const solicitud = this.solicitudSeleccionada();
    if (!solicitud) return;

    this.isSubmitting.set(true);
    this.solicitudService.eliminar(solicitud.id).subscribe({
      next: () => {
        this.isSubmitting.set(false);
        this.cerrarModalEliminar();
        this.mostrarNotificacion('Solicitud eliminada correctamente.');
        this.cargarDatos();
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.cerrarModalEliminar();
        this.errorMessage.set(
          'No se pudo eliminar la solicitud. Es posible que requieras permisos de administrador.',
        );
      },
    });
  }

  togglePrioridad(solicitud: Solicitud): void {
    this.solicitudService.cambiarPrioridad(solicitud.id).subscribe({
      next: () => {
        this.mostrarNotificacion('Prioridad actualizada exitosamente.');
        this.cargarDatos();
      },
      error: (err) => {
        this.errorMessage.set('Solo administradores y líderes pueden cambiar la prioridad.');
      },
    });
  }

  cambiarEstado(solicitud: Solicitud, nuevoEstado: EstadoSolicitud): void {
    this.solicitudService.cambiarEstado(solicitud.id, nuevoEstado).subscribe({
      next: () => {
        this.mostrarNotificacion(`Estado cambiado a ${nuevoEstado}.`);
        this.cargarDatos();
      },
      error: (err) => {
        const msg = err.error?.message || err.error?.error || 'Transición de estado no válida.';
        this.errorMessage.set(msg);
      },
    });
  }

  obtenerNombreLider(solicitud: Solicitud): string {
    if (solicitud.lider?.nombre) {
      return solicitud.lider.nombre;
    }
    return 'Líder asignado';
  }

  formatearFecha(fecha: string): string {
    if (!fecha) return '-';
    try {
      const d = new Date(fecha);
      return d.toLocaleDateString('es-ES', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });
    } catch {
      return fecha;
    }
  }

  mostrarNotificacion(msg: string): void {
    this.successMessage.set(msg);
    setTimeout(() => {
      this.successMessage.set(null);
    }, 4000);
  }
}
