import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Address } from 'src/app/models/address.model';
import { ContactData } from 'src/app/models/contactData.model';
import { Person } from 'src/app/models/person.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  
  constructor(private authService: AuthService) {}

  onSubmit(signUpForm: NgForm) {

    const formValue = signUpForm.value;

    const newPerson = new Person(
      formValue.personalCode,
      formValue.firstName,
      formValue.lastName,
      formValue.password,
      new ContactData(
        formValue.email,
        formValue.phone,
        new Address(
          formValue.country,
          formValue.county,
          formValue.street,
          formValue.number,
          formValue.postalIndex
        )
      )
    );

    this.authService.signUp(newPerson).subscribe();
  }
}
