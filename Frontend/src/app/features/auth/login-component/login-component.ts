import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service'; // Ajusta la ruta
import { LoaderService } from '../../../core/services/loader-service'; // Ajusta la ruta
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login-component.html'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = ''; // Para mostrar errores del backend

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private loaderService: LoaderService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      usuario: ['', [Validators.required, Validators.minLength(3)]],
      clave: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loaderService.show(); // Activamos el pez nadando
      this.errorMessage = '';

      const { usuario, clave } = this.loginForm.value;

      this.authService.login({ usuario, clave }).subscribe({
        next: (response) => {
          // Si el login es exitoso en Spring Boot (redirige a /)
          this.router.navigate(['/admin']).then(() => {
            this.loaderService.hide();
          });
        },
        error: (err) => {
          this.loaderService.hide();
          this.errorMessage = 'Usuario o contraseña incorrectos';
          console.error('Error en login:', err);
        }
      });
    }
  }
}