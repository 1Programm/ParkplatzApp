import { Kennzeichen } from "../Kennzeichen";

export class BuchungUebersichtDto {
    
    datum: Date;
    mitarbeiterName: string;
    parkplatzKennung: string;
    tagespreis: number;
    kennzeichen: Kennzeichen;
    
}