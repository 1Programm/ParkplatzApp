
import { Kennzeichen } from "../Kennzeichen";

export interface BuchungDto {
    
    buchungID: number

    datum: Date
    tagespreis: number;
    parkplatzKennung: string
    kennzeichen: Kennzeichen;
}