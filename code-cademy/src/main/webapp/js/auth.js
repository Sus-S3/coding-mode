class AuthManager {
  constructor() {
    this.currentUser = null;
    this.init();
  }

  async init() {
    // Verificar estado de sesión del servidor
    console.log('AuthManager init started');
    await this.checkSessionStatus();
    console.log('AuthManager init completed');
  }

  async checkSessionStatus() {
    try {
      console.log('Checking session status...');
      const response = await fetch('session-status');
      const data = await response.json();
      
      console.log('Session status response:', data);
      
      if (data.authenticated) {
        this.currentUser = {
          id: data.id,
          nombre: data.nombre,
          email: data.email,
          rol: data.rol,
          isAdmin: data.isAdmin
        };
        console.log('User authenticated:', this.currentUser);
        this.updateNavigation();
      } else {
        this.currentUser = null;
        console.log('User not authenticated');
        this.updateNavigation();
      }
    } catch (error) {
      console.error('Error checking session status:', error);
      this.currentUser = null;
      this.updateNavigation();
    }
  }

  updateNavigation() {
    const navAuth = document.getElementById('navAuth');
    
    if (!navAuth) {
      console.log('navAuth element not found');
      return;
    }

    console.log('Updating navigation, currentUser:', this.currentUser);

    if (this.currentUser) {
      // Usuario autenticado
      navAuth.innerHTML = `
        <div class="user-menu" id="userMenu">
          <span class="user-name">Hola, ${this.currentUser.nombre} \u25bc</span>
          <div class="user-dropdown" id="userDropdown">
            <a href="profile.jsp" class="dropdown-item">\ud83d\udc64 Mi Perfil</a>
            ${this.currentUser.isAdmin ? '<a href="dashboard" class="dropdown-item">\u2699\ufe0f Dashboard</a>' : ''}
            <a href="#" onclick="Auth.logout()" class="dropdown-item">\ud83d\udeaa Cerrar Sesi\u00f3n</a>
          </div>
        </div>
      `;
      // Eliminado: this.setupUserMenu();
    } else {
      // Usuario no autenticado
      navAuth.innerHTML = `
        <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
        <a href="register.jsp" class="btn btn-primary">Registrarse</a>
      `;
    }
  }

  async login(email, password) {
    try {
      const formData = new URLSearchParams();
      formData.append('email', email);
      formData.append('password', password);
      
      const response = await fetch('login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString()
      });
      
      const data = await response.json();
      
      if (data.success) {
        // Actualizar estado de sesión
        await this.checkSessionStatus();
        return { success: true, redirect: data.redirect };
      } else {
        return { success: false, message: data.message };
      }
    } catch (error) {
      console.error('Error during login:', error);
      return { success: false, message: 'Error de conexión' };
    }
  }

  async register(userData) {
    try {
      const formData = new URLSearchParams();
      formData.append('nombre', userData.nombre);
      formData.append('email', userData.email);
      formData.append('telefono', userData.telefono);
      formData.append('pais', userData.pais);
      formData.append('password', userData.password);
      
      const response = await fetch('register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString()
      });
      
      const data = await response.json();
      
      if (data.success) {
        // Actualizar estado de sesión
        await this.checkSessionStatus();
        return { success: true, redirect: data.redirect };
      } else {
        return { success: false, message: data.message };
      }
    } catch (error) {
      console.error('Error during registration:', error);
      return { success: false, message: 'Error de conexión' };
    }
  }

  async logout() {
    try {
      const response = await fetch('logout', {
        method: 'POST'
      });
      
      const data = await response.json();
      
      if (data.success) {
        this.currentUser = null;
        this.updateNavigation();
        // Redirigir al index
        window.location.href = 'index.jsp';
      }
    } catch (error) {
      console.error('Error during logout:', error);
      // Forzar logout local
      this.currentUser = null;
      this.updateNavigation();
      window.location.href = 'index.jsp';
    }
  }

  getCurrentUser() {
    console.log('Getting current user:', this.currentUser);
    return this.currentUser;
  }

  isAuthenticated() {
    return this.currentUser !== null;
  }

  isAdmin() {
    return this.currentUser && this.currentUser.isAdmin;
  }
}

// Instancia global
const Auth = new AuthManager();

// Verificar sesión al cargar la página
document.addEventListener('DOMContentLoaded', () => {
  console.log('Auth.js DOMContentLoaded event fired');
  // La verificación de sesión ya se hace en main.js, no necesitamos duplicarla aquí
});
