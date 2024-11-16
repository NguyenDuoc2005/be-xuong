<template>
  <div class="container-fluid mt-2">
    <h2 class="mb-4">Product</h2>
    <!--  loading-->
    <div class="loader" v-if="isLoading">
      <div class="spinner-border" role="status"></div>
    </div>
    <!-- Filter Form -->
    <form class="form mb-3 row">
      <div class="form-group mr-3 col-2">
        <label for="name" class="mr-2">Name</label>
        <input
          type="text"
          class="form-control"
          id="name"
          v-model="searchParams.name"
          @input="searchProductsAll()"
        />
      </div>

      <div class="form-group mr-3 col-2">
        <label for="price" class="mr-2">Price</label>
        <input
          type="number"
          class="form-control"
          id="price"
          v-model="searchParams.price"
          @input="searchProductsAll()"
        />
      </div>

      <div class="form-group mr-3 col-2">
        <label for="brand" class="mr-2">Brand</label>
        <select
          class="form-control w100"
          id="brand"
          v-model="searchParams.brand"
          @change="searchProductsAll()"
        >
          <option value="" selected>All</option>
          <option v-for="brand in brands" :key="brand" :value="brand.id">
            {{ brand.brandName }}
          </option>
        </select>
      </div>

      <div class="form-group mr-3 col-2">
        <label for="category" class="mr-2">Category</label>
        <select
          class="form-control"
          id="category"
          v-model="searchParams.category"
          @change="searchProductsAll()"
        >
          <option selected value="">All</option>
          <option
            v-for="category in categories"
            :key="category"
            :value="category.id"
          >
            {{ category.cateName }}
          </option>
        </select>
      </div>

      <div class="form-group mr-3 col-2">
        <label for="status" class="mr-2">Status</label>
        <select
          class="form-control"
          id="status"
          v-model="searchParams.status"
          @change="searchProductsAll()"
        >
          <option selected value="">All</option>
          <option
            v-for="productStatus in status"
            :key="productStatus"
            :value="productStatus.id"
          >
            {{ productStatus.statusName }}
          </option>
        </select>
      </div>

      <div class="mb-2">
        <button
          type="button"
          class="btn btn-success mb-2 btn-circle"
          @click="searchProductsAll()"
        >
          <i class="fas fa-search"></i>
          <!-- Biểu tượng tìm kiếm -->
        </button>
      </div>
    </form>
    <!-- Add Button -->
    <div class="mb-3">
      <button class="btn btn-primary" @click="openModalAdd()">
        Add Product
      </button>
    </div>
    <!-- Product Table -->
    <table class="table table-striped">
      <thead>
        <tr>
          <th>No</th>
          <th>Product Name</th>
          <th>Brand Name</th>
          <th>Subcategory</th>
          <th>Price</th>
          <th>Status</th>
          <th>Function</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(product, index) in products" :key="product.id">
          <td>{{ index + 1 + currentPage * pageSize }}</td>
          <td>{{ product.productName }}</td>
          <td>{{ product.brandName }}</td>
          <td>{{ product.subCategory }}</td>
          <td>{{ formatCurrency(product.sellPrice) }}</td>
          <td>{{ product.statusName }}</td>
          <td>
            <button class="btn btn-success" @click="openModalDetails(product)">
              <i class="fas fa-eye"></i>
            </button>
            <a class="btn btn-warning" @click="openModalEdit(product)">
              <i class="fas fa-pencil-alt"></i>
            </a>
            <a
              class="btn btn-danger"
              @click="deleteProduct(product.idProduct, product.idBrand)"
            >
              <i class="fas fa-trash"></i>
            </a>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- Phân trang tổng-->
    <div class="d-flex justify-content-between">
      <span
        >Showing {{ currentPage + 1 }} to {{ totalPages }} of
        {{ totalPages }} entries</span
      >
      <!-- Phân trang -->
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-end">
          <li class="page-item" :class="{ disabled: currentPage === 0 }">
            <a
              class="page-link"
              href="#"
              @click.prevent="changePage(currentPage - 1)"
              >Previous</a
            >
          </li>
          <li
            class="page-item"
            v-for="(page, index) in totalPages"
            :key="index"
            :class="{ active: currentPage === index }"
          >
            <a class="page-link" href="#" @click.prevent="changePage(index)">{{
              index + 1
            }}</a>
          </li>
          <li
            class="page-item"
            :class="{ disabled: currentPage === totalPages - 1 }"
          >
            <a
              class="page-link"
              href="#"
              @click.prevent="changePage(currentPage + 1)"
              >Next</a
            >
          </li>
        </ul>
      </nav>
    </div>
    <!-- Modal Product Details -->
    <div class="modal fade" id="productModalDetails" tabindex="-1">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Detail Product</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <!-- Form chi tiết sản phẩm details-->
            <form>
              <!-- name -->
              <div class="form-group row">
                <label for="productName" class="col-4 col-form-label text-right"
                  >Name</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="productName"
                    v-model="selectedProduct.productName"
                    readonly
                  />
                </div>
              </div>
              <!-- color -->
              <div class="form-group row">
                <label
                  for="productColor"
                  class="col-4 col-form-label text-right"
                  >Color</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="productColor"
                    v-model="selectedProduct.color"
                    readonly
                  />
                </div>
              </div>
              <!-- quantity -->
              <div class="form-group row">
                <label
                  for="productQuantity"
                  class="col-4 col-form-label text-right"
                  >Quantity</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="productQuantity"
                    v-model="selectedProduct.quantity"
                    readonly
                  />
                </div>
              </div>
              <!-- sell price -->
              <div class="form-group row">
                <label
                  for="productSellPrice"
                  class="col-4 col-form-label text-right"
                  >Sell price</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="productSellPrice"
                    v-model="selectedProduct.sellPrice"
                    readonly
                  />
                </div>
              </div>
              <!-- origin price -->
              <div class="form-group row">
                <label
                  for="productOriginPrice"
                  class="col-4 col-form-label text-right"
                  >Origin price</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="productOriginPrice"
                    v-model="selectedProduct.originPrice"
                    readonly
                  />
                </div>
              </div>
              <!-- brand name -->
              <div class="form-group row">
                <label
                  for="productBrand"
                  class="col-4 col-form-label text-right"
                  >Brand Name</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="productBrand"
                    v-model="selectedProduct.brandName"
                    disabled
                  >
                    <option
                      v-for="brand in products"
                      :key="brand"
                      :value="brand.brandName"
                    >
                      {{ brand.brandName }}
                    </option>
                    <!-- Add more options as needed -->
                  </select>
                </div>
              </div>
              <!--sibcategory -->
              <div class="form-group row">
                <label
                  for="productSubcategory"
                  class="col-4 col-form-label text-right"
                  >Subcategory</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="productSubcategory"
                    v-model="selectedProduct.subCategory"
                    disabled
                  >
                    <option
                      v-for="subcategory in products"
                      :key="subcategory"
                      :value="subcategory.subCategory"
                    >
                      {{ subcategory.subCategory }}
                    </option>
                    <!-- Add more options as needed -->
                  </select>
                </div>
              </div>
              <!-- status -->
              <div class="form-group row">
                <label
                  for="productStatus"
                  class="col-4 text-right col-form-label"
                  >Status</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="productStatus"
                    v-model="selectedProduct.statusName"
                    disabled
                  >
                    <option
                      v-for="productStatus in products"
                      :key="productStatus"
                      :value="productStatus.statusName"
                    >
                      {{ productStatus.statusName }}
                    </option>
                    <!-- Add more options as needed -->
                  </select>
                </div>
              </div>
              <!-- footer -->
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-secondary"
                  data-dismiss="modal"
                >
                  Close
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal Add Product -->
    <div class="modal fade" id="productModalAdd" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exModalLabel">Add Product</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <!-- Form  Add Product -->
            <form>
              <!-- Product Name -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.productName
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductName"
                  class="col-4 col-form-label text-right"
                  >Name</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="addProductName"
                    v-model="selectedProduct.productName"
                    required
                  />
                </div>
              </div>
              <!-- Color -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.color
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductColor"
                  class="col-4 col-form-label text-right"
                  >Color</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="addProductColor"
                    v-model="selectedProduct.color"
                    required
                  />
                </div>
              </div>
              <!-- Quantity -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.quantity
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductQuantity"
                  class="col-4 col-form-label text-right"
                  >Quantity</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="addProductQuantity"
                    v-model="selectedProduct.quantity"
                    required
                    min="1"
                  />
                </div>
              </div>
              <!-- Sell Price -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.sellPrice
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductSellPrice"
                  class="col-4 col-form-label text-right"
                  >Sell price</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="addProductSellPrice"
                    v-model="selectedProduct.sellPrice"
                    required
                    min="1"
                  />
                </div>
              </div>
              <!-- Origin Price -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.originPrice
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductOriginPrice"
                  class="col-4 col-form-label text-right"
                  >Origin price</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="addProductOriginPrice"
                    v-model="selectedProduct.originPrice"
                    required
                    min="1"
                  />
                </div>
              </div>
              <!-- Brand Name -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.brandName
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductBrand"
                  class="col-4 col-form-label text-right"
                  >Brand Name</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="addProductBrand"
                    v-model="selectedProduct.brandName"
                  >
                    <option
                      v-for="brand in brands"
                      :key="brand.id"
                      :value="brand.brandName"
                    >
                      {{ brand.brandName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- Subcategory -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.subCategory
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductSubcategory"
                  class="col-4 col-form-label text-right"
                  >Subcategory</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="addProductSubcategory"
                    v-model="selectedProduct.subCategory"
                  >
                    <option
                      v-for="subcategory in subcategories"
                      :key="subcategory.id"
                      :value="subcategory.subCateName"
                    >
                      {{ subcategory.subCateName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- Status -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.statusName
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductStatus"
                  class="col-4 col-form-label text-right"
                  >Status</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="addProductStatus"
                    v-model="selectedProduct.statusName"
                  >
                    <option
                      v-for="productStatus in status"
                      :key="productStatus.id"
                      :value="productStatus.statusName"
                    >
                      {{ productStatus.statusName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- Modal Footer -->
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-success"
                  @click="submitformAdd"
                >
                  Save changes
                </button>
                <button
                  type="button"
                  class="btn btn-secondary"
                  data-dismiss="modal"
                >
                  Close
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal Edit Product -->
    <div class="modal fade" id="productModalEdit" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exModalLabelEdit">Add Product</h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <!-- form edit product -->
            <form>
              <!-- name -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.productName
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductName"
                  class="col-4 col-form-label text-right"
                  >Name</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="editProductName"
                    v-model="selectedProduct.productName"
                    required
                  />
                </div>
              </div>
              <!-- color -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.color
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductColor"
                  class="text-right col-4 col-form-label"
                  >Color</label
                >
                <div class="col-8">
                  <input
                    type="text"
                    class="form-control"
                    id="editProductColor"
                    v-model="selectedProduct.color"
                    required
                  />
                </div>
              </div>
              <!-- quantity -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.quantity
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductQuantity"
                  class="col-4 col-form-label text-right"
                  >Quantity</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="editProductQuantity"
                    v-model="selectedProduct.quantity"
                    required
                  />
                </div>
              </div>
              <!-- sell price -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.sellPrice
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductSellPrice"
                  class="col-4 col-form-label text-right"
                  >Sell price</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="editProductSellPrice"
                    v-model="selectedProduct.sellPrice"
                    required
                  />
                </div>
              </div>
              <!-- origin price -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.originPrice
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="addProductOriginPrice"
                  class="col-4 col-form-label text-right"
                  >Origin price</label
                >
                <div class="col-8">
                  <input
                    type="number"
                    class="form-control"
                    id="editProductOriginPrice"
                    v-model="selectedProduct.originPrice"
                    required
                  />
                </div>
              </div>
              <!-- brand name -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.brandName
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="productBrand"
                  class="col-4 col-form-right text-right"
                  >Brand Name</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="editProductBrand"
                    v-model="selectedProduct.brandName"
                  >
                    <option
                      v-for="brand in brands"
                      :key="brand"
                      :value="brand.brandName"
                    >
                      {{ brand.brandName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- subcategory -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.subcategory
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="productSubcategory"
                  class="col-4 col-form-label text-right"
                  >Subcategory</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="editProductSubcategory"
                    v-model="selectedProduct.subCategory"
                  >
                    <option
                      v-for="subcategory in subcategories"
                      :key="subcategory"
                      :value="subcategory.subCateName"
                    >
                      {{ subcategory.subCateName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- status -->
              <div class="row">
                <small class="text-danger error col-11 text-right">{{
                  messageErrors.status
                }}</small>
              </div>
              <div class="form-group row">
                <label
                  for="productStatus"
                  class="col-4 col-form-label text-right"
                  >Status</label
                >
                <div class="col-8">
                  <select
                    class="form-control"
                    id="editProductStatus"
                    v-model="selectedProduct.statusName"
                  >
                    <option
                      v-for="productStatus in status"
                      :key="productStatus"
                      :value="productStatus.statusName"
                    >
                      {{ productStatus.statusName }}
                    </option>
                  </select>
                </div>
              </div>
              <!-- footer -->
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="submitFormEdit()"
                >
                  Edit
                </button>
                <button
                  type="button"
                  class="btn btn-secondary ml-auto"
                  data-dismiss="modal"
                >
                  Close
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import Swal from "sweetalert2";
import accounting from "accounting";

export default {
  data() {
    return {
      validationErrors: {},
      isLoading: false,
      products: [], // Danh sách sản phẩm
      currentPage: 0, // Trang hiện tại
      totalPages: 0, // Tổng số trang
      pageSize: 3, // Số lượng sản phẩm trên mỗi trang
      totalProductsAllPage: 0, // tổng số sp trên tất cả các trang
      selectedProduct: {
        idProduct: "",
        productName: "",
        color: "",
        quantity: "",
        sellPrice: "",
        originPrice: "",
        brandName: "",
        subCategory: "",
        statusName: "",
      },
      // thông báo lỗi
      messageErrors: {
        productName: "",
        color: "",
        quantity: "",
        sellPrice: "",
        originPrice: "",
        brandName: "",
        subCategory: "",
        statusName: "",
      },
      returnErrors: {
        productName: false,
        color: false,
        quantity: false,
        sellPrice: false,
        originPrice: false,
        brandName: false,
        subCategory: false,
        statusName: false,
      },
      // lọc và tìm kiếm product
      searchParams: {
        name: "",
        price: "",
        brand: "",
        category: "",
        status: "",
      },
      brands: [
        {
          id: "",
          brandName: "",
        },
      ],
      status: [
        {
          id: "",
          statusName: "",
        },
      ],
      subcategories: [
        {
          id: "",
          subCateName: "",
          subCateCode: "",
        },
      ],
      categories: [
        {
          id: "",
          cateCode: "",
          cateName: "",
        },
      ],
    };
  },
  mounted() {
    this.fetchProducts();
    this.getBrands();
    this.getStatus();
    this.getCategory();
  },
  methods: {
    // hàm hiển thị product theo page
    fetchProducts(page = 0) {
      axios
        .get(`/api/products`, {
          params: {
            page: page,
            size: this.pageSize,
          },
        })
        .then((response) => {
          if (
            response.data &&
            response.data._embedded &&
            response.data._embedded.productResponseList
          ) {
            this.products = response.data._embedded.productResponseList; // Lấy danh sách sản phẩm từ API
            this.totalPages = response.data.page.totalPages; // Tổng số trang
            this.currentPage = response.data.page.number; // Trang hiện tại
            this.totalProductsAllPage = response.data.page.totalElements; // tổng số sp trên tất các trang
            console.log(this.products);
          } else {
            console.error("Định dạng phản hồi không hợp lệ:", response.data);
          }
        })
        .catch((error) => {
          console.error("Đã xảy ra lỗi khi tải sản phẩm:", error);
        });
    },
    // Hàm thay đổi trang
    changePage(page) {
      if (page >= 0 && page < this.totalPages) {
        if(this.searchParams.name!=""|| this.searchParams.price!=""||
          this.searchParams.brand!="" || this.searchParams.category!="" ||
          this.searchParams.status!=""
        ){
        this.searchProductsAll(page);
      }else this.fetchProducts(page);
      }
    },
    // hàm lấy brand
    getBrands() {
      axios.get("/api/brands").then((response) => {
        this.brands = response.data;
      });
    },
    // hàm lấy status
    getStatus() {
      axios.get("/api/status").then((response) => {
        this.status = response.data;
      });
    },
    // hàm lấy subcategory
    getSubCategory() {
      axios.get("/api/subcategory").then((response) => {
        this.subcategories = response.data;
      });
    },
    // hàm lấy category
    getCategory() {
      axios.get("/api/category").then((response) => {
        this.categories = response.data;
      });
    },
    // hàm mở modal details
    openModalDetails(product) {
      this.selectedProduct = { ...product };
      $("#productModalDetails").modal("show"); // Mở modal
    },
    //Hàm mở modal add product
    openModalAdd() {
      this.selectedProduct = {
        // Khởi tạo lại dữ liệu cho form thêm sản phẩm
        idProduct: "",
        idBrand: "",
        productName: "",
        color: "",
        quantity: "",
        sellPrice: "",
        originPrice: "",
        brandName: "",
        subCategory: "",
        statusName: "",
      };
      (this.messageErrors = {
        productName: "",
        color: "",
        quantity: "",
        sellPrice: "",
        originPrice: "",
        brandName: "",
        subCategory: "",
        statusName: "",
      }),
        (this.returnError = {
          productName: false,
          color: false,
          quantity: false,
          sellPrice: false,
          originPrice: false,
          brandName: false,
          subCategory: false,
          statusName: false,
        }),
        this.getSubCategory();
      this.getBrands();
      this.getStatus();
      $("#productModalAdd").modal("show"); // Mở modal
    },
    // mở modal edit
    openModalEdit(product) {
      (this.messageErrors = {
        productName: "",
        color: "",
        quantity: "",
        sellPrice: "",
        originPrice: "",
        brandName: "",
        subCategory: "",
        statusName: "",
      }),
        (this.returnError = {
          productName: false,
          color: false,
          quantity: false,
          sellPrice: false,
          originPrice: false,
          brandName: false,
          subCategory: false,
          statusName: false,
        }),
        this.getSubCategory();
      this.getBrands();
      this.getStatus();
      this.selectedProduct = { ...product };
      $("#productModalEdit").modal("show"); // Mở modal
    },
    // validate form , check các trường hợp trc khi submit
    validateForm() {
      if (this.selectedProduct.productName == "") {
        this.messageErrors.productName = "Tên sản phẩm không được để trống";
        this.returnErrors.productName = false;
      } else {
        this.messageErrors.productName = "";
        this.returnErrors.productName = true;
      }
      if (this.selectedProduct.color == "") {
        this.messageErrors.color = "Color không được để trống";
        this.returnErrors.color = false;
      } else {
        this.messageErrors.color = "";
        this.returnErrors.color = true;
      }
      if (this.selectedProduct.quantity == "") {
        this.messageErrors.quantity = "Số lượng không được để trống";
        this.returnErrors.quantity = false;
      } else if (this.selectedProduct.quantity < 1) {
        this.messageErrors.quantity = "Số lượng phải >0";
        this.returnErrors.quantity = false;
      } else {
        this.messageErrors.quantity = "";
        this.returnErrors.quantity = true;
      }
      if (this.selectedProduct.sellPrice == "") {
        this.messageErrors.sellPrice = "Giá Bán không được để trống";
        this.returnErrors.sellPrice = false;
      } else if (this.selectedProduct.sellPrice < 1) {
        this.messageErrors.sellPrice = "Giá Bán không được <1";
        this.returnErrors.sellPrice = false;
      } else {
        this.messageErrors.sellPrice = "";
        this.returnErrors.sellPrice = true;
      }
      if (this.selectedProduct.originPrice == "") {
        this.messageErrors.originPrice = "Giá Nhập không được để trống";
        this.returnErrors.originPrice = false;
      } else if (this.selectedProduct.originPrice < 1) {
        this.messageErrors.originPrice = "Giá Nhập  không được <1";
        this.returnErrors.originPrice = false;
      } else if (
        this.selectedProduct.originPrice > this.selectedProduct.sellPrice
      ) {
        this.messageErrors.originPrice = "Giá nhập không được > giá bán";
        this.returnErrors.originPrice = false;
      } else {
        this.messageErrors.originPrice = "";
        this.returnErrors.originPrice = true;
      }
      if (this.selectedProduct.brandName == "") {
        this.messageErrors.brandName = "Hãng không được để trống";
        this.returnErrors.brandName = false;
      } else {
        this.messageErrors.brandName = "";
        this.returnErrors.brandName = true;
      }
      if (this.selectedProduct.subCategory == "") {
        this.messageErrors.subCategory = "Thể loại không được để trống";
        this.returnErrors.subCategory = false;
      } else {
        this.messageErrors.subCategory = "";
        this.returnErrors.subCategory = true;
      }
      if (this.selectedProduct.statusName == "") {
        this.messageErrors.statusName = "Trạng thái không được để trống";
        this.returnErrors.statusName = false;
      } else {
        this.messageErrors.statusName = "";
        this.returnErrors.statusName = true;
      }
      return;
    },
    // submit form add
    submitformAdd() {
      // validate form add
      this.validateForm();
      if (
        !this.returnErrors.color ||
        !this.returnErrors.productName ||
        !this.returnErrors.quantity ||
        !this.returnErrors.sellPrice ||
        !this.returnErrors.originPrice ||
        !this.returnErrors.brandName ||
        !this.returnErrors.subCategory ||
        !this.returnErrors.statusName
      ) {
        return;
      }
      this.isLoading = true; 
      // Gửi yêu cầu API để thêm sản phẩm
      axios
        .post("/api/products/add", this.selectedProduct)
        .then((response) => {
          this.fetchProducts(this.currentPage); // Cập nhật lại danh sách sản phẩm
          $("#productModalAdd").modal("hide"); // Đóng modal
          Swal.fire("Thêm thành công", response.data, "success");
        })
        .catch((error) => {
          if (error.response && error.response.status === 400) {
            // Lấy lỗi từ response và lưu vào biến errors
            this.messageErrors = error.response.data;
          } else {
            alert(error.response);
          }
        }).finally(()=>{
          setTimeout(() => {
            this.isLoading = false; 
          }, 1000);
        });
    },
    // submit form edit
    submitFormEdit() {
      this.validateForm();
      if (
        !this.returnErrors.color ||
        !this.returnErrors.productName ||
        !this.returnErrors.quantity ||
        !this.returnErrors.sellPrice ||
        !this.returnErrors.originPrice ||
        !this.returnErrors.brandName ||
        !this.returnErrors.subCategory ||
        !this.returnErrors.statusName
      ) {
        return;
      }
      this.isLoading = true;
      // Gửi yêu cầu API để sửa sản phẩm
      axios
        .put(
          `/api/products/${this.selectedProduct.idProduct}/${this.selectedProduct.idBrand}`,
          this.selectedProduct
        )
        .then((response) => {
          console.log(response.data);
          this.fetchProducts(this.currentPage); // Cập nhật lại danh sách sản phẩm
          $("#productModalEdit").modal("hide"); // Đóng modal
          Swal.fire("Cập nhật thành công", response.data, "success");
        })
        .catch((error) => {
          if (error.response && error.response.status === 400) {
            // Lấy lỗi từ response và lưu vào biến errors
            this.messageErrors = error.response.data;
          } else {
            this.messageErrors = error.response.data;
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.isLoading = false;
          }, 2000);
        });
    },
    // Hàm xóa sản phẩm
    deleteProduct(productId, brandId) {
      this.isLoading = true; 
      if (confirm("Xác Nhận Xóa?")) {
        axios
          .delete(`/api/products/${productId}/${brandId}`)
          .then((response) => {
            // Cập nhật lại danh sách sản phẩm
            // Kiểm tra nếu không còn sản phẩm nào trên trang hiện tại
            if (this.products.length === 1 && this.currentPage > 0) {
              // Quay lại trang trước nếu hiện tại chỉ còn 1 sản phẩm
              this.changePage(this.currentPage - 1);
            } else if (this.products.length === 1 && this.currentPage == 0) {
              this.products = null;
              this.fetchProducts(0);
            } else {
              // Load lại danh sách sản phẩm
              this.fetchProducts(this.currentPage);
            }
            Swal.fire("Xóa thành công", response.data, "success");
          })
          .catch((error) => {
            Swal.fire("Xóa thất bại", error, "danger");
          }).finally(()=>{
            setTimeout(() => {
              this.isLoading = false; 
            }, 1000);
          });
      }else{
        this.isLoading = false; 
      }
    },
    // hàm tìm kiếm sản phẩm
    searchProductsAll(page = 0) {
      axios
        .post(`/api/products/search `, {
          name: this.searchParams.name,
          price: this.searchParams.price,
          brand: this.searchParams.brand,
          status: this.searchParams.status,
          category: this.searchParams.category,
          page: page,
          size: this.pageSize,
        })
        .then((response) => {
          if (
            response.data &&
            response.data._embedded &&
            response.data._embedded.productResponseList
          ) {
            this.products = response.data._embedded.productResponseList; // Lấy danh sách sản phẩm từ API
            this.totalPages = response.data.page.totalPages; // Tổng số trang
            this.currentPage = response.data.page.number; // Trang hiện tại
            this.totalProductsAllPage = response.data.page.totalElements; // tổng số sp trên tất các trang
            console.log(this.products);
          } else {
            console.error("Định dạng phản hồi không hợp lệ:", response.data);
            this.products = null;
          }
        })
        .catch((error) => {
          console.error("Đã xảy ra lỗi khi tải sản phẩm:", error);
        });
    },
    // format tiền
    formatCurrency(value) {
      const formattedValue = accounting.formatMoney(value, "", 0, ".", ",");
      return `${formattedValue} VNĐ`;
    },
  },
};
</script>
<style scoped>
.form-group {
  margin: 5px;
}
.loader {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100%;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
</style>
