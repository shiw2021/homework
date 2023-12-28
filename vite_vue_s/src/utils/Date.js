import axios from "axios";

export function randomDate() {
    const startDate = new Date(2000, 0, 1);
    const endDate = new Date(2023, 0, 1);
    return new Date(startDate.getTime() + Math.random() * (endDate.getTime() - startDate.getTime()));
}


