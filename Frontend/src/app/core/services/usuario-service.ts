import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Usuario } from '../../shared/interfaces/perfil';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/usuarios/api';

  constructor(private http: HttpClient) { }

  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<any>(`${this.apiUrl}/listar`, { withCredentials: true })
      .pipe(
        map(response => response.data)
      );
  }

  guardarUsuario(usuario: Usuario): Observable<any> {
    return this.http.post(`${this.apiUrl}/guardar`, usuario);
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  cambiarEstado(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/cambiar-estado/${id}`, {});
  }
}
