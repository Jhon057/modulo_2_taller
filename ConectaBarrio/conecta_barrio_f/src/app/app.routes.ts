import { Routes } from '@angular/router';
import { Auth } from './componentes/auth/auth';
import { Dashboard } from './componentes/dashboard/dashboard';
import { authGuard, publicOnlyGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: Auth,
    canActivate: [publicOnlyGuard]
  },
  {
    path: 'dashboard',
    component: Dashboard,
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'dashboard'
  }
];
