import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { LoginResponse } from '../../shared/interfaces/perfil';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: { usuario: string, clave: string }) {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(res => {
          if (res.token) {
            localStorage.setItem('token', res.token);
            localStorage.setItem('nombreUsuario', res.nombre);
            localStorage.setItem('perfil', res.perfil);
            localStorage.setItem('usuario', res.usuario);
          }
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getNombre(): string | null {
    return localStorage.getItem('nombreUsuario');
  }

  getPerfil(): string | null {
    return localStorage.getItem('perfil');
  }

  isLoggedIn(): boolean {
    return !!this.getToken(); // ✅ útil para guards
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']); // ✅ redirige al login
  }
}