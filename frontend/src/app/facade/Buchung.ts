import { Mitarbeiter } from "./Mitarbeiter"
import { Parkplatz } from "./Parkplatz"

export interface Buchung {
    buchungID: number
    datum: Date
    tagespreis: number
    mitarbeiter: Mitarbeiter
    parkplatz: Parkplatz
    
}