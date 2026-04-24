import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Perfil } from '../../shared/interfaces/perfil';

@Injectable({
  providedIn: 'root',
})
export class PerfilService {
  private apiUrl = 'http://localhost:8080/perfiles';

  constructor(private http: HttpClient) { }

  listarPerfiles() {
    return this.http.get<{ success: boolean, data: Perfil[] }>
      (`${this.apiUrl}/api/listar`)
      .pipe(map(res => res.data));
  }

}
