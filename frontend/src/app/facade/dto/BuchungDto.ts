import { Interface } from "readline";
import { Kennzeichen } from "../Kennezeichen";

export interface BuchungDto {
    
    buchungID: number

    datum: Date
    tagespreis: number;
    parkplatzKennung: string
    kennzeichen: Kennzeichen;
}