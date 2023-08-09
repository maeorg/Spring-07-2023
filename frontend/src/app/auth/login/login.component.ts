import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {


  onSubmit(loginForm: NgForm) {

    const formValue = loginForm.value;

    console.log(formValue.email);

  }
}
