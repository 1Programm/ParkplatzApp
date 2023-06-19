import { Kennzeichen } from "./Kennzeichen"
import { Verstoss } from "./Verstoss";

export interface Mitarbeiter {
    
    id: number;
    vorname: string;
    nachname: string;
    mail: string;
    kennzeichenList: Kennzeichen[];
    verstossList: Verstoss[];
};

