import { Address } from "./address.model";

export class ContactData {
    constructor(
        public email:string,
        public phone:string,
        public address: Address,
        public id?:number
    ) {}
}