package com.task.newsfeedapp.model


import com.google.gson.annotations.SerializedName

data class ArticleResponse (

    @SerializedName("status"    ) var status    : String?   = null,
    @SerializedName("copyright" ) var copyright : String?   = null,
    @SerializedName("response"  ) var response  : Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs.Meta.Response? = Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs.Meta.Response()

){
    data class Legacy (

        @SerializedName("xlarge"       ) var xlarge       : String? = null,
        @SerializedName("xlargewidth"  ) var xlargewidth  : Int?    = null,
        @SerializedName("xlargeheight" ) var xlargeheight : Int?    = null

    ){
        data class Multimedia (

            @SerializedName("rank"      ) var rank     : Int?    = null,
            @SerializedName("subtype"   ) var subtype  : String? = null,
            @SerializedName("caption"   ) var caption  : String? = null,
            @SerializedName("credit"    ) var credit   : String? = null,
            @SerializedName("type"      ) var type     : String? = null,
            @SerializedName("url"       ) var url      : String? = null,
            @SerializedName("height"    ) var height   : Int?    = null,
            @SerializedName("width"     ) var width    : Int?    = null,
            @SerializedName("legacy"    ) var legacy   : Legacy? = Legacy(),
            @SerializedName("subType"   ) var subType  : String? = null,
            @SerializedName("crop_name" ) var cropName : String? = null

        ){
            data class Headline (

                @SerializedName("main"           ) var main          : String? = null,
                @SerializedName("kicker"         ) var kicker        : String? = null,
                @SerializedName("content_kicker" ) var contentKicker : String? = null,
                @SerializedName("print_headline" ) var printHeadline : String? = null,
                @SerializedName("name"           ) var name          : String? = null,
                @SerializedName("seo"            ) var seo           : String? = null,
                @SerializedName("sub"            ) var sub           : String? = null

            ){
                data class Keywords (

                    @SerializedName("name"  ) var name  : String? = null,
                    @SerializedName("value" ) var value : String? = null,
                    @SerializedName("rank"  ) var rank  : Int?    = null,
                    @SerializedName("major" ) var major : String? = null

                ){
                    data class Person (

                        @SerializedName("firstname"    ) var firstname    : String? = null,
                        @SerializedName("middlename"   ) var middlename   : String? = null,
                        @SerializedName("lastname"     ) var lastname     : String? = null,
                        @SerializedName("qualifier"    ) var qualifier    : String? = null,
                        @SerializedName("title"        ) var title        : String? = null,
                        @SerializedName("role"         ) var role         : String? = null,
                        @SerializedName("organization" ) var organization : String? = null,
                        @SerializedName("rank"         ) var rank         : Int?    = null

                    ){
                        data class Byline (

                            @SerializedName("original"     ) var original     : String?           = null,
                            @SerializedName("person"       ) var person       : ArrayList<Person> = arrayListOf(),
                            @SerializedName("organization" ) var organization : String?           = null

                        ){
                            data class Docs (

                                @SerializedName("abstract"         ) var abstract       : String?               = null,
                                @SerializedName("web_url"          ) var webUrl         : String?               = null,
                                @SerializedName("snippet"          ) var snippet        : String?               = null,
                                @SerializedName("lead_paragraph"   ) var leadParagraph  : String?               = null,
                                @SerializedName("source"           ) var source         : String?               = null,
                                @SerializedName("multimedia"       ) var multimedia     : ArrayList<Multimedia> = arrayListOf(),
                                @SerializedName("headline"         ) var headline       : Headline?             = Headline(),
                                @SerializedName("keywords"         ) var keywords       : ArrayList<Keywords>   = arrayListOf(),
                                @SerializedName("pub_date"         ) var pubDate        : String?               = null,
                                @SerializedName("document_type"    ) var documentType   : String?               = null,
                                @SerializedName("news_desk"        ) var newsDesk       : String?               = null,
                                @SerializedName("section_name"     ) var sectionName    : String?               = null,
                                @SerializedName("byline"           ) var byline         : Byline?               = Byline(),
                                @SerializedName("type_of_material" ) var typeOfMaterial : String?               = null,
                                @SerializedName("_id"              ) var Id             : String?               = null,
                                @SerializedName("word_count"       ) var wordCount      : Int?                  = null,
                                @SerializedName("uri"              ) var uri            : String?               = null

                            ){
                                data class Meta (

                                    @SerializedName("hits"   ) var hits   : Int? = null,
                                    @SerializedName("offset" ) var offset : Int? = null,
                                    @SerializedName("time"   ) var time   : Int? = null

                                )

                                {

                                    data class Response (

                                        @SerializedName("docs" ) var docs : ArrayList<Docs> = arrayListOf(),
                                        @SerializedName("meta" ) var meta : Meta?           = Meta()

                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}