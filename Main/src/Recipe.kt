class Recipe constructor() {
    var title: String                           //navnet på oppskriften.
    var neededIngredients: Array<Ingredient>    //en liste med ingrediensene i retten.
    var procedure: String                       //steg for steg instrukser om hvordan man lager retten.
    var picture                                 //et bilde av den ferdige retten.
    var allergens: Array<String>                //en liste med allergener som befinner seg i retten.
    var rating: Int                             //en rating fra 1-5 som viser hvor bra folk likte retten.
    var difficultyLevel: String                 //en infotekst om retten er vanskelig å lage (lett, medium vanskelig).
    var servings: String                        //en tekst med en knapp hvor du velger hvor mange porsjoner du trenger.
    var time: Int                               //tiden det tar å lage retten.


    fun displayInfo() {                         //vis all infoen om oppskriften
        println("Title: $title")
        println("Ingredients: ${neededIngredients.joinToString()}")
        println("Procedure: $procedure")
        println("Allergens: ${allergens.joinToString()}")
        pritnln("Rating: $rating")
        println("Difficulty Level: $difficultyLevel")
        println("Servings: $servings")
        println("Time: $time minutes")
    }

    fun searchForRecipe()      {                //søk etter oppskriften basert på ord.

    }

    fun filterRecipe() {                        //filter oppskrifter basert på type, som allergener, tid eller ingredienser.

    }

    fun changeServingSize() {                   //endrer porsjoner, øker også ingrediensene som trengs.

    }

    //Oppskriften klassen inneholder oppskrifter og alt relevant informasjon, i tillegg kan brukere søke og filtrere basert på søkeord og filtreringstype.

}
