'use client';

import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
//Redireciona de '/' para '/chat'
export default function Page() {
  const router = useRouter();

  useEffect(() => {
    router.push('/chat');
  }, [router]);

  return null; // ou um loading se quiser
}