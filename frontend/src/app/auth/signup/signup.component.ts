import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Address } from 'src/app/models/address.model';
import { ContactData } from 'src/app/models/contactData.model';
import { Person } from 'src/app/models/person.model';
import { PersonService } from 'src/app/services/person.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  
  constructor(private personService: PersonService) {}

  onSubmit(addPersonForm: NgForm) {

    const formValue = addPersonForm.value;

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

    this.personService.addPerson(newPerson).subscribe();
  }
}
