import { Component } from '@angular/core';
import { NavbarComponent } from './navbar-component/navbar-component';
import { FooterComponent } from './footer-component/footer-component';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [NavbarComponent, FooterComponent],
  templateUrl: './home-component.html',

})
export class HomeComponent { }
