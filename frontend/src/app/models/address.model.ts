export class Address {
    constructor(
        public country:string,
        public county:string,
        public street:string,
        public number:string,
        public postalIndex:string,
        public id?:number
    ) {}
}