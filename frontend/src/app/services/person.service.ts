import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Person } from '../models/person.model';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private url = environment.baseUrl + "/persons";

  constructor(private httpClient: HttpClient) { }

  addPerson(person: Person) {
    return this.httpClient.post(this.url, person);
  }
}
