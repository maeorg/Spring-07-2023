import { ContactData } from "./contactData.model";

export class Person {
    constructor(
        public personalCode:string,
        public firstName:string,
        public lastName:string,
        public password:string,
        public contactData: ContactData,
        public id?:number
    ) {}
}