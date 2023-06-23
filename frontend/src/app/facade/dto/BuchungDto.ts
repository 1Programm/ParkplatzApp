import { Kennzeichen } from "../Kennzeichen";

export class BuchungDto {
    
    buchungID: number

    datum: Date
    tagespreis: number;
    parkplatzKennung: string
    kennzeichen: Kennzeichen;
}