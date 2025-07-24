# spring-ecommerce-product

## feature list

- [x] configure API that services the following requests:
  - [x] add
  - [x] retrieve
  - [x] update
  - [x] delete

- [x] UI representation of products
  - [x] retrieve
  - [x] add
  - [x] update
  - [x] delete

- [x] database integration (instead of global properties)

- [x] validation
  - product name:
    - [x] must be no more than 15 characters, including spaces
    - [x] Allowed special characters: `( )`, `[ ]`, `+`, `-`, `&`, `/`, `_` (all other special characters are not allowed)
    - [x] the name must be unique across all products
  - product price:
    - [x] must be greater than 0
  - product image URL:
    - [x] must be start with `http://` or `https://`

- [ ] auth
  - [ ] register new member with email and password
  - [ ] send token in response to register request
  - [ ] login exist member with email and password
  - [ ] send token in response to login request

