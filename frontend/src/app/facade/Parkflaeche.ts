import { Parkplatz } from "./Parkplatz"

export interface Parkflaeche {
    parkflaechID: number
    bezeichnung: string
    parkplatzList: Parkplatz[]
}