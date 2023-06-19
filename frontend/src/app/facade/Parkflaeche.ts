import { Parkplatz } from "./Parkplatz"

export interface Parkflaeche {
    parkflaecheID: number
    bezeichnung: string
    parkplatzList: Parkplatz[]
}