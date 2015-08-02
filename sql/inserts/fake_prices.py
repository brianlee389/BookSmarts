#!/usr/bin/env python

import random

vendors = ['BookItBuyback', 'Bookbyte', 'Chegg', 'TextbookRecycling', 'ValoreBooks', 'Textbooks', 'TextbookRush', 'BookStores', 'RentText', 'CKY']
urls = ['http://www.bookitbuyback.com/', 'https://www.bookbyte.com/', 'http://www.chegg.com/', 'http://www.textbookrecycling.com/', 'http://www.valorebooks.com/', 'http://www.textbooks.com/', 'http://www.textbookrush.com/', 'http://www.bookstores.com/', 'http://www.renttext.com/', 'https://www.ckybooks.com/']
isbns = ['0262510871', '0262010631', '0262541327', '0131103628', '0134190440', '0201100886', '0131873253', '0321455363', '0201000237', '0137903871']

quotes = []
book_quotes = []


def add_quote(quote_id, price, url):
    row = {"id" : quote_id, "price": price, "quoted_at" : "now()", "url" : url}
    quotes.append(row)

def add_book_quote(vendor, isbn, quote_id):
    row = {"vendor_name" : vendor, "isbn": isbn, "quote_id" : quote_id}
    book_quotes.append(row)

quote_id = 1
for isbn in isbns:
    # pick a default price to start with for the given book
    base_price = random.randint(10, 200)
    for vendor, url in zip(vendors, urls):
        # modify the price by up to +- $10
        price = base_price + (random.random() * random.choice([10,-10]))
        add_quote(quote_id, price, url)
        add_book_quote(vendor, isbn, quote_id)
        quote_id += 1


print "insert into bookquotes (vendor_name, book_isbn, quote_id) VALUES"
for bookquote in book_quotes[:-1]:
    print "('%s', '%s', %d)," % (bookquote["vendor_name"], bookquote["isbn"], bookquote["quote_id"])
bookquote = book_quotes[-1]
print "('%s', '%s', %d);" % (bookquote["vendor_name"], bookquote["isbn"], bookquote["quote_id"])

print "insert into quotes (id, price, quoted_at, url) VALUES"
for quote in quotes[:-1]:
    print "(%d, %.2f, '%s', '%s')," % (quote["id"], quote["price"], quote["quoted_at"], quote["url"])
quote = quotes[-1]
print "(%d, %.2f, '%s', '%s');" % (quote["id"], quote["price"], quote["quoted_at"], quote["url"])


