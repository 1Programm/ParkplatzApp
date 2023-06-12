import { Parkflaeche } from "./Parkflaeche"

export interface Parkhaus {
    parkhausID: number
    bezeichnung: string
    straße: string
    hausnummer: number
    plz: number
    ort: string
    parkflaecheList: Parkflaeche[]
}