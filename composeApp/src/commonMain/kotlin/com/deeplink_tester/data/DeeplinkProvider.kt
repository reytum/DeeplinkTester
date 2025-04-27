package com.deeplink_tester.data

import com.deeplink_tester.data.models.BaseUrl

class DeeplinkProvider {
    companion object {
        const val URL_SCHEME = "https://"
        const val LOCALHOST_SCHEME = "http://"
        const val PORT_LENGTH = 5

        fun getBaseUrls(isWeb: Boolean): List<BaseUrl> {
            val genericList = mutableListOf(
                BaseUrl(name = "QA AppLink", url = "app-link-np.kotaksecurities.com"),
                BaseUrl(name = "QA Domain", url = "q-ntrade.kotaksecurities.online"),
                BaseUrl(name = "QA One Link", url = "kneoqa.onelink.me"),
                BaseUrl(name = "Prod AppLink", url = "app-link.kotaksecurities.com"),
                BaseUrl(name = "Prod Domain", url = "neo.kotaksecurities.com"),
                BaseUrl(name = "Prod One Link", url = "kneo.onelink.me"),
            )
            if (isWeb) {
                genericList.add(BaseUrl(name = "Localhost", url = "localhost"))
            }
            return genericList
        }
    }
}


const val deeplinkJson = """
 {
    "Post Landing Links": [
    {
        "name": "StockCase",
        "url": "/stockcase"
    },
    {
        "name": "StockCase Golden Opportunity Basket",
        "url": "/stockcase/basket/uninvested/9e03e221-85f1-11ed-9667-02d27c588048"
    },
    {
        "name": "Sipit",
        "url": "/sipIt"
    },
    {
        "name": "Sipit Listing",
        "url": "/sipIt/listing"
    },
    {
        "name": "Sipit IOC Page",
        "url": "/sipIt/stock/NSE/IOC"
    },
    {
        "name": "Mutual Fund",
        "url": "/mutualfund"
    },
    {
         "name": "Start SIP with 100",
         "url": "/mutualfund/mutual-funds?fund_type_id=5"
    },
    {
        "name": "Funds Page",
        "url": "/funds"
    },
    {
         "name": "Deposit Rs 500",
         "url": "/deposit?amount=500"
    },
    {
        "name": "Home Page Stocks",
        "url": "/home?tab=stocks"
    },
    {
        "name": "Home Page FNO",
        "url": "/home?tab=fno" 
    },
    {
        "name": "Invest Page",
        "url": "/invest" 
    },
    {
        "name": "Investment Ideas",
        "url": "/investment-ideas"
    },
    {
        "name": "Search Page with Query HDFC",
        "url": "/home?searchQuery=HDFC"
    },
    {
        "name": "Search Page LTP with Query HDFC",
        "url": "/home?searchQueryV2=HDFC"
    },
    {
        "name": "Watchlist Page",
        "url": "/watchlist"
    }
    ],
    "Pre Login Links": [
    {
        "name": "NeoMobile",
        "url": "/neomobile"
    }
    ],
    "SIO Links": [
     {
        "name": "Activate Trinity",
        "url": "/sio/TRIN"
     }
    ],
    "MKSAPI Links": [
      {
        "name": "Income Range Update",
        "url": "/mksapi/irup"
      },
      {
         "name": "Nest Trading terminal",
         "url": "/nest-trading-terminal"
      }
    ]
}
"""