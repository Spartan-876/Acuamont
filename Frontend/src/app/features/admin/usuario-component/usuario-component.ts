import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../core/services/usuario-service';
import { LoaderService } from '../../../core/services/loader-service';
import { Usuario } from '../../../shared/interfaces/perfil';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuario-component.html'
})
export class UsuarioComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(
    private usuarioService: UsuarioService,
    private loaderService: LoaderService
  ) { }

  ngOnInit(): void {
    this.obtenerUsuarios();
  }

  obtenerUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loaderService.hide();
      },
      error: (err) => {
        console.error(err);
        this.loaderService.hide();
      }
    });
  }

  eliminarUsuario(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
      this.usuarioService.eliminarUsuario(id).subscribe(() => {
        this.obtenerUsuarios();
      });
    }
  }

  editarUsuario(user: Usuario) {
    console.log('Editando:', user);
  }
}