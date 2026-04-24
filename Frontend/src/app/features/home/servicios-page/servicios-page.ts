import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar-component/navbar-component';
import { FooterComponent } from '../footer-component/footer-component';

@Component({
  selector: 'app-servicios-page',
  standalone: true,
  imports: [NavbarComponent, FooterComponent],
  templateUrl: './servicios-page.html',
})
export class ServiciosPage { }
