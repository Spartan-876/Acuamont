import { Component, OnInit } from '@angular/core';
import { Perfil } from '../../../shared/interfaces/perfil';
import { CommonModule } from '@angular/common';
import { PerfilService } from '../../../core/services/perfil-service';
import { LoaderService } from '../../../core/services/loader-service';

@Component({
  selector: 'app-perfiles-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfiles-component.html',
})
export class PerfilesComponent implements OnInit {
  perfiles: Perfil[] = [];

  constructor(
    private perfilService: PerfilService,
    private loaderService: LoaderService
  ) { }

  ngOnInit(): void {
    this.obtenerPerfiles();
  }

  obtenerPerfiles(): void {
    this.perfilService.listarPerfiles().subscribe({
      next: (data) => {
        this.perfiles = data;
        this.loaderService.hide();
      },
      error: (err) => {
        console.error(err);
        this.loaderService.hide();
      }
    });
  }


}
